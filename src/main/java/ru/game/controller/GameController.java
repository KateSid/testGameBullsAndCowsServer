package ru.game.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.game.domain.ApplicationUser;
import ru.game.domain.UserAttempt;
import ru.game.repos.UserRepo;

import java.util.*;

@CrossOrigin
@RestController
public class GameController {
    @Autowired
    private UserRepo userRepo;
    private ArrayList<UserAttempt> attempts;
    private ArrayList<Integer> guessedNumber;
    private String username;

    @GetMapping("/getAttempts")
    public ArrayList<UserAttempt> getAttempts(){
        return attempts;
    }

    @GetMapping("/start")
    public ArrayList<UserAttempt> start(){
        Random random=new Random();
        attempts=new ArrayList<UserAttempt>();
        guessedNumber=new ArrayList<Integer>();
        int n0=random.nextInt(9)+1;
        ArrayList<Integer> number = new ArrayList<Integer>();
        for (int i=0; i<10;i++){
            number.add(i);
        }
        number.remove(n0);
        Collections.shuffle(number);
        guessedNumber.add(n0);
        guessedNumber.add(number.get(0));
        guessedNumber.add(number.get(1));
        guessedNumber.add(number.get(2));
        return attempts;
    }

    @GetMapping("/userTurn/{number}")
    public HashMap<String, String> uTurn(@PathVariable("number") String number){
        HashMap<String, String> response = new HashMap<>();
        int b=0;
        int c=0;
        int digit=Integer.parseInt(number);
        UserAttempt attempt=new UserAttempt();
        ArrayList<Integer> turn=parseToList(number);
        for (int i=0; i<4;i++){
            for (int j=0; j<4;j++){
                if (guessedNumber.get(i).equals(turn.get(j)))
                    if (i==j) b++;
                    else c++;
            }
        }
        attempt.setuNumber(digit);
        attempt.setBools(b);
        attempt.setCows(c);
        attempt.setAttemptNumb(attempts.size()+1);
       if (b==4) {
            ApplicationUser userFromDb = userRepo.findByUsername(username);
            userFromDb.setPoints( userFromDb.getPoints()+attempts.size());
            userFromDb.setGames(userFromDb.getGames()+1);
            userFromDb.setAverage(userFromDb.getPoints()/userFromDb.getGames());
            userRepo.save(userFromDb);
            response.put("Resp", "Win");
            return response;
        }
        attempts.add(attempt);
        response.put("Resp", "Continue");
        return response;
    }


    @GetMapping("/currentUserInfo/{username}")
    public HashMap<String, Long>  userInfo(@PathVariable("username") String username) {
        this.username=username;
        ApplicationUser udb=userRepo.findByUsername(username);
        HashMap<String, Long> user = new HashMap<>();
        user.put("average",udb.getAverage());
        user.put("points",udb.getPoints());
        user.put("games",udb.getGames());
        return user;
    }

    private ArrayList<Integer> parseToList(String string){
        int uNumb=Integer.parseInt(string);
        ArrayList<Integer> list=new ArrayList<Integer>();
        list.add(uNumb/1000);
        uNumb=uNumb-uNumb/1000*1000;
        list.add(uNumb/100);
        uNumb=uNumb-uNumb/100*100;
        list.add(uNumb/10);
        uNumb=uNumb-uNumb/10*10;
        list.add(uNumb);
        return list;
    }

}
