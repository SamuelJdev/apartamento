package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.Apto;
import sis.apartamento.model.Locacao;
import sis.apartamento.repository.LocacaoRepository;
import sis.apartamento.service.exceptions.AptoOcupadoException;
import sis.apartamento.service.exceptions.BussinesException;
import sis.apartamento.service.exceptions.LocacaoNaoEncontradoException;
import sis.apartamento.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LocacaoService {
    @Autowired
    private LocacaoRepository locacaoRepository;
    @Autowired
    private AptoService aptoService;
    private final String LOCACAO_NAO_ENCONRTADA = "O Locacao não foi encontrato!";
    private final String DISPONIVEL = "DISPONIVEL";
    private final String FECHADO = "FECHADO";
    private final String OCUPADO = "OCUPADO";
    private final String APTO_OCUPADO = "Apto Ocupado";

    public List<Locacao> listarTodos() {
        return locacaoRepository.findAll();
    }

    public Locacao buscarPorId(Long id) {
        Locacao locacao = locacaoRepository.findById(id).orElse(null);
        if (locacao == null) {
            throw new LocacaoNaoEncontradoException(LOCACAO_NAO_ENCONRTADA);
        }
        return locacao;
    }

    public Locacao inserir(Locacao locacao) {
        Apto apto = aptoService.buscarPorId(locacao.getApto().getId());
        if (apto.getStatusApartamento().equals(DISPONIVEL)) {
            locacao.setApto(apto);
            apto.setStatusApartamento(OCUPADO);
            aptoService.editar(apto);
        } else {
            throw new AptoOcupadoException(APTO_OCUPADO + apto.getId());
        }
        locacao.setStatusLocacao("ABERTO"); // Salvar com registro é um set, salvar locacao com statusde aberto
        locacao.setDataEntrada(new Date(System.currentTimeMillis()));
        locacao.setDataSaida(DateUtils.calculaDataMaisUmMes(locacao.getDataEntrada()));

        /* Pegar valor locacao que é informado no Postman e subtrair com o valor Pago, onde valor debito recebe valor pago menos valor locacao. */
        locacao.setValorDebito(locacao.getValorLocacao().subtract(locacao.getValorPago()));
        locacao.setIsFechar(false);
        return locacaoRepository.save(locacao);
    }

    public void editar(Locacao locacao) {
        verificarExistencia(locacao);
        locacao.setValorDebito(locacao.getValorLocacao().subtract(locacao.getValorPago()));
        if (locacao.getIsFechar()) {
            if (locacao.getValorDebito().equals(new BigDecimal("0.00"))) {
                locacao.setDataSaida(new Date(System.currentTimeMillis()));
                locacao.setStatusLocacao(FECHADO);
                Apto apto = aptoService.buscarPorId(locacao.getApto().getId());
                apto.setStatusApartamento(DISPONIVEL);
                aptoService.editar(apto);
                locacaoRepository.save(locacao);
            } else {
                throw new BussinesException("Debito em aberto " + locacao.getValorDebito());
            }
        }
    }

    private void verificarExistencia(Locacao locacao) {
        buscarPorId(locacao.getId());
    }

    public void deletar(Long id) {
        try {
            locacaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LocacaoNaoEncontradoException(LOCACAO_NAO_ENCONRTADA);
        }
    }
}
