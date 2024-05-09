package ru.anykeyers.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.statistics.domain.ServiceMetric;

@Repository
public interface ServicesRepository extends JpaRepository<ServiceMetric, Integer> {

    ServiceMetric findByCarWashId(Long carWashId);

}
