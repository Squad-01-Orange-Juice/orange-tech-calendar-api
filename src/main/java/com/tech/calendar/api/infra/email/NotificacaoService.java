package com.tech.calendar.api.infra.email;

import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.EventoRepository;
import com.tech.calendar.api.domain.usuario.Usuario;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Scheduled(cron = "0 54 19 * * ?") // Executar todos os dias às 09h
    public void enviarNotificacoes() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario usuario: usuarios) {

            List<Evento> eventos = eventoRepository.findByInscritos_Id(usuario.getId());

            for (Evento evento: eventos) {
                System.out.println(evento.getDataInicio().toLocalDate().minusDays(7));
                System.out.println(LocalDate.now());
                if(evento.getDataInicio().toLocalDate().minusDays(7).isEqual(LocalDate.now())){
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("squad1.orangej@gmail.com");
                    message.setTo(usuario.getLogin());
                    message.setText("O evento " + evento.getNome() + " que você se inscreveu acontece em 7 dias");
                    message.setSubject("Lembrete de evento");

                    this.emailSender.send(message);
                }

            }

        }
    }
}
