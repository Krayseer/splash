package ru.anykeyers.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.statistics.domain.ServiceMetric;

/**
 * DAO для работы со статистикой
 */
@Repository
public interface ServicesRepository extends JpaRepository<ServiceMetric, Integer> {

    /**
     * Получить метрики для автомойки
     *
     * @param carWashId идентификатор автомойки
     */
    ServiceMetric findByCarWashId(Long carWashId);

}
