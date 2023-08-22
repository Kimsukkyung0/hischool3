package com.green.secondproject.common.config.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum RoleType {
    TC("ROLE_TC", "학생 권한"),
    STD("ROLE_STD", "선생님 권한");

    private final String code;
    private final String displayName;
}
