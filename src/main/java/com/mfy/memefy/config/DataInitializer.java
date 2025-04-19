package com.mfy.memefy.config;

/**
 * The {@link DataInitializer} class
 *
 * @author Oleh Ivasiuk
 */
import com.mfy.memefy.entity.MemeEntity;
import com.mfy.memefy.repository.MemeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * The {@link DataInitializer} class
 *
 * @author Oleh Ivasiuk
 */
//@Component
//public class DataInitializer implements CommandLineRunner {
//    private final MemeRepository memeRepository;
//    private final Random random = new Random();
//
//    public DataInitializer(MemeRepository memeRepository) {
//        this.memeRepository = memeRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//        if (memeRepository.count() == 0) {
//            for (long i = 1; i <= 10; i++) {
//                MemeEntity meme = new MemeEntity();
//                meme.setName("Meme " + i);
//                meme.setImageUrl("https://example.com/meme" + i + ".jpg");
//                meme.setLikes(random.nextLong(100));
//                memeRepository.save(meme);
//            }
//        }
//    }
//}
