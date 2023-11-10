package br.com.startrip.backend.controller;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.controller.response.InformacaoReservaResponse;
import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Periodo;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.service.reserva.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private RealizarReservaService realizarReservaService;

    @Autowired
    private ListarReservaSolicitanteService listarReservaSolicitanteService;

    @Autowired
    private ListarReservaAnuncianteService listarReservaAnuncianteService;

    @Autowired
    private PagarReservaService pagarReservaService;

    @Autowired
    private CancelarReservaService cancelarReservaService;

    @Autowired
    private EstornarReservaService estornarReservaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InformacaoReservaResponse realizarUmaReserva(@RequestBody CadastrarReservaRequest cadastrarReservaRequest) {
        return realizarReservaService.realizarUmaReserva(cadastrarReservaRequest);
    }

    @GetMapping("/solicitante/id/{idSolicitante}")
    public Page<Reserva> listarReservaSolicitante(@PageableDefault(sort = "periodoDataHoraFinal", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long idSolicitante, Periodo periodo) {
        return listarReservaSolicitanteService.listarReservaDeSolicitante(pageable, idSolicitante, periodo);
    }

    @GetMapping("/anunciante/id/{idAnunciante}")
    public Page<Reserva> listarReservaDeAnunciante(@PageableDefault(sort = "periodoDataHoraFinal", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long idAnunciante) {
        return listarReservaAnuncianteService.listarReservaDeAnunciante(pageable, idAnunciante);
    }

    @PutMapping("/pagamento/id/{idReserva}")
    public void pagarReserva(@PathVariable Long idReserva, @RequestBody FormaPagamento formaPagamento) {
        pagarReservaService.pagarReserva(idReserva, formaPagamento);
    }

    @PutMapping("/cancelamento/id/{idReserva}")
    public void cancelarReserva(@PathVariable Long idReserva) {
        cancelarReservaService.cancelarReserva(idReserva);
    }

    @PutMapping("/estorno/id/{idReserva}")
    public void estornarReserva(@PathVariable Long idReserva) {
        estornarReservaService.estornarReserva(idReserva);
    }

}
