package com.password926.agijagi.growth.infrastructure;

import com.password926.agijagi.child.domain.Gender;
import com.password926.agijagi.growth.domain.StandardGrowth;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface StandardGrowthRepository extends Repository<StandardGrowth, Integer> {

    List<StandardGrowth> findAllByMonthLessThanEqualAndGender(int month, Gender gender);
}
