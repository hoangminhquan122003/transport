package com.transporthc.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.transporthc.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T,ID> implements BaseRepository<T,ID> {
    EntityManager em;
    JPAQueryFactory query;
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.query=new JPAQueryFactory(em);
    }
}
