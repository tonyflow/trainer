package com.hackathon.trainer.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hackathon.trainer.TrainerApplication;
import com.hackathon.trainer.elasticsearch.configuration.ElasticSearchConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={TrainerApplication.class,ElasticSearchConfiguration.class})
public abstract class AbstractTrainerTest {

}
