package com.green.secondproject.common.repository;

import com.green.secondproject.common.config.security.model.RoleType;
import com.green.secondproject.common.entity.QUserEntity;
import com.green.secondproject.common.entity.QVanEntity;
import com.green.secondproject.teacher.model.QStudentVo;
import com.green.secondproject.teacher.model.StudentVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QUserEntity u = QUserEntity.userEntity;
    private final QVanEntity v = QVanEntity.vanEntity;

    @Override
    public List<StudentVo> studentList(String name, Long vanId) {
        return jpaQueryFactory
                .select(new QStudentVo(u.userId, u.nm, u.birth))
                .from(u)
                .join(u.vanEntity, v)
                .where(v.vanId.eq(vanId), u.aprYn.eq(1), u.roleType.eq(RoleType.STD), nameSearch(name))
                .fetch();
    }

    private BooleanExpression nameSearch(String name) {
        return name != null ? u.nm.contains(name) : null;
    }
}
