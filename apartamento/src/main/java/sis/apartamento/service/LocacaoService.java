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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final Integer VALOR_ALUGUEL = 300;
    private final Integer VALOR_MES = 30;


    @Override
    public List<Locacao> listarTodos() {
        return locacaoRepository.findAll();
    }

    @Override
    public Locacao buscarPorId(Long id) {
        return locacaoRepository.findById(id).orElseThrow(() -> new LocacaoNaoEncontradoException(id));
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

        if (locacao.getIsDiaria()) {
            locacao.setDia(ChronoUnit.DAYS.between(locacao.getDataEntrada(), locacao.getDataSaida()));
            BigDecimal dias = new BigDecimal(locacao.getDia());
            locacao.setValorDiaria(dias.multiply(new BigDecimal(VALOR_ALUGUEL).divide(new BigDecimal(VALOR_MES))));
        } else {
            locacao.setDia(ChronoUnit.DAYS.between(locacao.getDataEntrada(), locacao.getDataSaida()));
            locacao.setValorDiaria(null);
        }

        locacao.setStatusLocacao("ABERTO");
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
                locacao.setDataSaida(LocalDate.now());
                locacao.setStatusLocacao(FECHADO);
                Apto apto = aptoService.buscarPorId(locacao.getApto().getId());
                apto.setStatusApartamento(DISPONIVEL);
                aptoService.editar(apto);
            } else {
                throw new NegocioException("Debito em aberto " + locacao.getValorDebito());
            }
        }

        if (locacao.getIsDiaria()) {
            locacao.setDia(ChronoUnit.DAYS.between(locacao.getDataEntrada(), locacao.getDataSaida()));
            BigDecimal dias = new BigDecimal(locacao.getDia());
            locacao.setValorDiaria(dias.multiply(new BigDecimal(VALOR_ALUGUEL).divide(new BigDecimal(VALOR_MES))));
        } else {
            locacao.setDia(ChronoUnit.DAYS.between(locacao.getDataEntrada(), locacao.getDataSaida()));
            locacao.setValorDiaria(null);
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