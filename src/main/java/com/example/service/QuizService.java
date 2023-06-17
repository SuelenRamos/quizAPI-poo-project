package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.model.Question;
import com.example.model.QuestionForm;
import com.example.model.Result;
import com.example.repository.QuestionRepo;
import com.example.repository.ResultRepo;

@Service
public class QuizService {

    private final QuestionRepo questionRepo;
    private final ResultRepo resultRepo;

    @Autowired
    public QuizService(QuestionRepo questionRepo, ResultRepo resultRepo) {
        this.questionRepo = questionRepo;
        this.resultRepo = resultRepo;
    }

    public QuestionForm getQuestions() {
        List<Question> allQues = questionRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        QuestionForm qForm = new QuestionForm();
        qForm.setQuestions(qList);

        return qForm;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for (Question q : qForm.getQuestions()) {
            if (q.getAns() == q.getChose()) {
                correct++;
            }
        }

        return correct;
    }

    public void saveScore(Result result) {
        resultRepo.save(result);
    }

    public List<Result> getTopScore() {
        return resultRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
    }
}