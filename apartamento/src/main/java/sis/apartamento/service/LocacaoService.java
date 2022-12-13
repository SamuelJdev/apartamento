package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.AptoOcupadoException;
import sis.apartamento.exception.LocacaoNaoEncontradoException;
import sis.apartamento.exception.NegocioException;
import sis.apartamento.model.Apto;
import sis.apartamento.model.Locacao;
import sis.apartamento.repository.LocacaoRepository;
import sis.apartamento.service.interfaces.ILocacao;
import sis.apartamento.util.DateUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LocacaoService implements ILocacao {
    @Autowired
    private LocacaoRepository locacaoRepository;
    @Autowired
    private AptoService aptoService;
    private final String DISPONIVEL = "DISPONIVEL";
    private final String FECHADO = "FECHADO";
    private final String OCUPADO = "OCUPADO";
    @Override
    public List<Locacao> listarTodos() {
        return locacaoRepository.findAll();
    }

    @Override
    public Locacao buscarPorId(Long id) {
        return locacaoRepository.findById(id).orElseThrow(()-> new LocacaoNaoEncontradoException(id));
    }

    @Override
    public Locacao inserir(Locacao locacao) {

        Apto apto = aptoService.buscarPorId(locacao.getApto().getId());
        if (apto.getStatusApartamento().equals(DISPONIVEL)) {
            locacao.setApto(apto);
            apto.setStatusApartamento(OCUPADO);
            aptoService.editar(apto);
        } else {
            throw new AptoOcupadoException(apto.getId());
        }
        locacao.setStatusLocacao("ABERTO"); // Salvar com registro é um set, salvar locacao com statusde aberto
        locacao.setDataEntrada(new Date(System.currentTimeMillis()));
        locacao.setDataSaida(DateUtils.calculaDataMaisUmMes(locacao.getDataEntrada()));

        /* Pegar valor locacao que é informado no Postman e subtrair com o valor Pago, onde valor debito recebe valor pago menos valor locacao. */
        locacao.setValorDebito(locacao.getValorLocacao().subtract(locacao.getValorPago()));
        locacao.setIsFechar(false);
        return locacaoRepository.save(locacao);
    }
    @Override
    public Locacao editar(Locacao locacao, Long id) {
        verificarExistencia(id);
        locacao.setValorDebito(locacao.getValorLocacao().subtract(locacao.getValorPago()));

        if (locacao.getIsFechar()) {
            if (locacao.getValorDebito().equals(new BigDecimal("0.00"))) {
                locacao.setDataSaida(new Date(System.currentTimeMillis()));
                locacao.setStatusLocacao(FECHADO);
                Apto apto = aptoService.buscarPorId(locacao.getApto().getId());
                apto.setStatusApartamento(DISPONIVEL);
                aptoService.editar(apto);
            } else {
                throw new NegocioException("Debito em aberto " + locacao.getValorDebito());
            }
        }
        locacaoRepository.save(locacao);
        return locacao;
    }

    private void verificarExistencia(Long id) {
        buscarPorId(id);
    }

    @Override
    public void deletar(Long id) {
        try {
            locacaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new LocacaoNaoEncontradoException(id);
        }
    }
}