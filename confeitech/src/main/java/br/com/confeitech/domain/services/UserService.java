package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.application.utils.Ordenacao;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.infra.persistence.mappers.UserMapper;
import br.com.confeitech.domain.models.UserModel;
import br.com.confeitech.infra.persistence.repositories.UserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserService implements Ordenacao<UserModel> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnderecoService enderecoService;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;



    private void getEmail(String destinatario, String assunto, String corpo) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de verificação");
            message.setText("seu código de verificação é:" + ThreadLocalRandom.current().nextInt(1000, 10000));
            Transport.send(message);
            System.out.println("E-mail enviado para: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail: " + e.getMessage(), e);
        }
    }

    public void fodase(String destinatario) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de verificação");
            message.setText("seu código de verificação é:" + ThreadLocalRandom.current().nextInt(1000, 10000));
            Transport.send(message);
            System.out.println("E-mail enviado para: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail: " + e.getMessage(), e);
        }
    }



    public void fodase(String destinatario, String username, String password, String host, String port) {
        System.out.println("oi");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de verificação");
            message.setText("seu código de verificação é:" + ThreadLocalRandom.current().nextInt(1000, 10000));
            Transport.send(message);
            System.out.println("E-mail enviado para: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail: " + e.getMessage(), e);
        }
    }





    /**
     * Recupera todos os usuários do repositório de usuários e os converte para uma lista de UserCreatedDTO.
     *
     * @return Uma lista de objetos UserCreatedDTO representando todos os usuários armazenados no repositório.
     */
    public List<UserCreatedDTO> getUsers() {

        List<UserModel> users = userRepository.findByActive(true);

        if(users.isEmpty()) {
            throw new ApplicationExceptionHandler(USERS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return  users// Busca todos os usuários do repositório
                .stream() // Converte a lista de UserModel para um Stream
                .map(userMapper::userModelToUserCreatedDTO) // Mapeia cada UserModel para um UserCreatedDTO
                .collect(Collectors.toList()); // Coleta o resultado do Stream em uma lista de UserCreatedDTO
    }

    public List<UserCreatedDTO> getUserPerAlphabeticalOrder() {

        List<UserModel> users = userRepository.findAll();

        if(users.isEmpty()) {
            throw new ApplicationExceptionHandler(USERS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        users = ordenarListaEmOrdemAlfabetica(users, 0, users.size() -1);

        return  users// Busca todos os usuários do repositório
                .stream() // Converte a lista de UserModel para um Stream
                .map(userMapper::userModelToUserCreatedDTO) // Mapeia cada UserModel para um UserCreatedDTO
                .collect(Collectors.toList()); // Coleta o resultado do Stream em uma lista de UserCreatedDTO
    }




    public UserCreatedDTO getUser(Long id) {
        UserModel user = getUserPerIdAndActive(id);

        return userMapper.userModelToUserCreatedDTO(user);
    }




    /**
     * Salva um novo usuário no repositório de usuários.
     *
     * @param userDTO Um objeto UserDTO contendo as informações do usuário a ser salvo.
     * @return Um objeto UserCreatedDTO representando o usuário recém-criado com as informações processadas.
     * @throws ApplicationExceptionHandler se um usuário com o mesmo e-mail já existir no repositório.
     */
    @Transactional
    public UserCreatedDTO saveUser(UserDTO userDTO) {

        // Verifica se o e-mail fornecido já está cadastrado no repositório de usuários
        // Lança uma exceção de conflito de e-mail se o e-mail já existir
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new ApplicationExceptionHandler(EMAIL_EXISTS, HttpStatus.CONFLICT);
        }

        // Converte o objeto UserDTO para um objeto UserModel utilizando o mapeador de usuários
        // Codifica a senha do usuário e a define no objeto UserModel
        // Salva o objeto UserModel no repositório de usuários
        UserModel user = userMapper.userDTOToUserModel(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        enderecoService.saveEndereco(userDTO.cep());
        userRepository.save(user);

        // Converte o objeto UserModel salvo para um objeto UserCreatedDTO e o retorna
        return userMapper.userModelToUserCreatedDTO(user);
    }




    /**
     * Atualiza um usuário existente no repositório de usuários.
     *
     * @param id      O ID do usuário a ser atualizado.
     * @param userDTO Um objeto UserDTO contendo as informações atualizadas do usuário.
     * @return Um objeto UserCreatedDTO representando o usuário atualizado com as informações processadas.
     * @throws ApplicationExceptionHandler se o usuário com o ID fornecido não for encontrado
     *                                     ou se o e-mail já estiver em uso por outro usuário.
     */
    public UserCreatedDTO updateUser(Long id, UserDTO userDTO) {

        //Recebe o usuário pelo id
        UserModel user = getUserPerId(id);

        if (!user.getEmail().equals(userDTO.email()) && userRepository.existsByEmail(userDTO.email())) {
            throw new ApplicationExceptionHandler(EMAIL_EXISTS, HttpStatus.CONFLICT);
        }

        // Atualiza os campos do usuário com as informações do UserDTO
        // Salva o usuário atualizado no repositório
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        user.setPhone(userDTO.phone());
        user.setBirthDate(LocalDate.parse(userDTO.birthDate()));
        user.setActive(userDTO.active());

        userRepository.save(user);

        // Converte o UserModel atualizado para um UserCreatedDTO e o retorna
        return userMapper.userModelToUserCreatedDTO(user);
    }




    /**
     * Desativa um usuário existente no repositório de usuários, definindo seu status como inativo.
     *
     * @param id O ID do usuário a ser desativado.
     * @throws ApplicationExceptionHandler se o usuário já estiver inativo.
     */
    public void deleteUser(Long id) {

        // Obtém o usuário pelo ID fornecido
        UserModel user = getUserPerId(id);

        // Verifica se o usuário já está inativo; se estiver, lança uma exceção
        if (!user.getActive()) {
            throw new ApplicationExceptionHandler(INACTIVE_USER, HttpStatus.BAD_REQUEST);
        }

        // Define o usuário como inativo
        user.setActive(false);

        // Salva a alteração no repositório
        userRepository.save(user);
    }


    public UserModel getUserPerId(Long id) {
        Optional<UserModel> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new ApplicationExceptionHandler(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        // Obtém o usuário do Optional, pois ele existe
        // Verifica se o e-mail foi alterado e se o novo e-mail já está em uso por outro usuário
        return optionalUser.get();
    }




    public UserCreatedDTO buscarPorEmailESenha(String email, String rawPassword) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new ApplicationExceptionHandler("Usuário não encontrado com as credenciais fornecidas.", HttpStatus.NOT_FOUND);
        }

        UserModel user = optionalUser.get();

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new ApplicationExceptionHandler("Usuário não encontrado com as credenciais fornecidas.", HttpStatus.NOT_FOUND);
        }

        return userMapper.userModelToUserCreatedDTO(user);
    }

    public UserModel getUserPerIdAndActive(Long id) {
        Optional<UserModel> optionalUser = userRepository.findByIdAndActive(id, true);

        if (optionalUser.isEmpty()) {
            throw new ApplicationExceptionHandler(USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        // Obtém o usuário do Optional, pois ele existe
        // Verifica se o e-mail foi alterado e se o novo e-mail já está em uso por outro usuário
        return optionalUser.get();
    }

    @Override
    public List<UserModel> ordenarListaEmOrdemAlfabetica(List<UserModel> listaDesordenada, int indMenor, int indMaior) {

        List<UserModel> listaOrdenada = listaDesordenada.stream()
                .sorted(Comparator.comparing(UserModel::getName))
                .collect(Collectors.toList());

        return listaOrdenada;
    }

    public List<UserModel> mapearParaModel(List<UserCreatedDTO> usuariosCriados) {

        List<UserModel> userModels = new ArrayList<>();

        for (UserCreatedDTO userCreatedDTO : usuariosCriados) {

            UserModel user = userMapper.userCreatedToUserModel(userCreatedDTO);
            userModels.add(user);
        }

        return userModels;

    }

    public List<UserCreatedDTO> mapearParaCreated(List<UserModel> usuarioCriados) {

        List<UserCreatedDTO> users = new ArrayList<>();

        for (UserModel userModel : usuarioCriados){

            UserCreatedDTO user = userMapper.userModelToUserCreatedDTO(userModel);
            users.add(user);
        }

        return users;


    }


}
