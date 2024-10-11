package com.password926.agijagi.child.infrastructure;

import com.password926.agijagi.child.domain.Child;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ChildRepository extends Repository<Child, Long> {

    Optional<Child> findByIdAndIsDeletedFalse(long id);

    Child save(Child child);
}
