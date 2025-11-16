package com.amazon.preferences;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, String> {
	public Preferences findByUserId(String userId);
}
