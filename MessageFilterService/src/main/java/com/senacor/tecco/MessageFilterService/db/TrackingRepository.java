package com.senacor.tecco.MessageFilterService.db;

import com.senacor.tecco.MessageFilterService.models.TrackingHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackingRepository extends MongoRepository<TrackingHistory, String> {
}
