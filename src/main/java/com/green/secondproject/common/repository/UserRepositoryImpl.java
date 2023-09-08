package com.green.secondproject.common.repository;

import com.green.secondproject.common.entity.QUserEntity;
import com.green.secondproject.teacher.model.QStudentVo;
import com.green.secondproject.teacher.model.StudentVo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QUserEntity u = QUserEntity.userEntity;
    @Override
    public List<StudentVo> studentList(String name) {
        return jpaQueryFactory
                .select(new QStudentVo(u.userId, u.nm, u.birth))
                .from(u)
                .where()
                .fetch();
    }
}
