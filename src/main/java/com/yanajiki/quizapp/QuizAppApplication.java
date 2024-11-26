package com.yanajiki.quizapp;

import com.yanajiki.quizapp.kiss.AnswerQuestionUseCase;
import com.yanajiki.quizapp.kiss.BuildQuizUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

    @Autowired
    private BuildQuizUseCase buildQuizUseCase;

    @Autowired
    private AnswerQuestionUseCase answerQuestionUseCase;

    @Override
    public void run(String... args) throws Exception {
//		QuizInfoDTO quizInfoDTO = buildQuizUseCase.execute(10, QuestionCategory.MATH);
//
//		answerQuestionUseCase.execute(quizInfoDTO.getId(), 2, "C");

//        DiscordClient client = DiscordClient.create("MTMwOTY4MjMyNTc5MzI4MDA1MQ.GWwfii.-YZKwRy87zh-YPWk6w62F76MJsxiTJHY4BURKk");
//        final GatewayDiscordClient client = DiscordClientBuilder
//                .create("MTMwOTY4MjMyNTc5MzI4MDA1MQ.GWwfii.-YZKwRy87zh-YPWk6w62F76MJsxiTJHY4BURKk")
//                .build()
//                .login()
//                .block();
////		Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
////				gateway.on(ReadyEvent.class, event ->
////						Mono.fromRunnable(() -> {
////							final User self = event.getSelf();
////							System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
////						})));
//
//        Map<String, Command> commands = new HashMap<>();
//
//        commands.put("ping", event -> event.getMessage().getChannel()
//                .flatMap(channel -> {
//                    System.out.println(channel.getLastMessage().);
//                    System.out.println(channel);
//                    return channel.createMessage("Pong!");
//                })
//                .then()
//        );
//
//        client.getEventDispatcher().on(MessageCreateEvent.class)
//                // 3.1 Message.getContent() is a String
//                .flatMap(event -> Mono.just(event.getMessage().getContent())
//                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
//                                // We will be using ! as our "prefix" to any command in the system.
//                                .filter(entry -> {
//                                    System.out.println(entry.getKey());
//                                    String command = entry.getKey();
//                                    return content.startsWith('!' + command);
//                                })
//                                .flatMap(entry -> entry.getValue().execute(event))
//                                .next()))
//                .subscribe();
////
//        client.onDisconnect().block();
    }
}
