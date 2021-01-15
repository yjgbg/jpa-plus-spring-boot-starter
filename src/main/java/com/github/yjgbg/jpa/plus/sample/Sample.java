package com.github.yjgbg.jpa.plus.sample;

import com.github.yjgbg.jpa.plus.specification.ExecutableSpecification;
import lombok.experimental.ExtensionMethod;

import javax.persistence.EntityManager;

@ExtensionMethod(Sample.class)
public class Sample {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        entityManager.sample().findAll();
    }

    public static ExecutableSpecification<Sample> sample(EntityManager that) {
        return new ExecutableSpecification<>(that, Sample.class);
    }
}
