CREATE TABLE abilities (    
    id INT AUTO_INCREMENT PRIMARY KEY,        -- Уникальный идентификатор способности
    hero_id INT,                              -- Идентификатор героя, которому принадлежит способность    
    ability_number INT NOT NULL,
    ability_url VARCHAR(255) NOT NULL,
    ability_name VARCHAR(255) NOT NULL,       -- Название способности    
    description TEXT,                         -- Описание способности
    characteristic_1 VARCHAR(255),    
    characteristic_2 VARCHAR(255),
    characteristic_3 VARCHAR(255),    
    characteristic_4 VARCHAR(255),
    
    distance VARCHAR(10),
    ability_range VARCHAR(10),    
    duration VARCHAR(10),
    charges INT,    
    charging_time VARCHAR(10),  
    сooldown VARCHAR(10),
    
    upgrade_1 VARCHAR(50),
    upgrade_2 VARCHAR(50),    
    upgrade_3 VARCHAR(50),
    FOREIGN KEY (hero_id) REFERENCES heroes(id)  -- Связь с таблицей героев по hero_id
);

INSERT INTO abilities(hero_id, ability_url, ability_name, description, characteristic_1, characteristic_2, characteristic_3, characteristic_4, distance, 
    ability_range, duration, charges, charging_time, сooldown, upgrade_1, upgrade_2, upgrade_3)
VALUES
(1, 1, "https://deadlocked.wiki/images/thumb/f/fd/Siphon_Life.png/45px-Siphon_Life.png", "Siphon Life", "Drain health from enemies in front of you while they are in the radius.", 
"35

Damage Per Second", "100%

Heal vs Heroes", "50%

Heal vs Non-Heroes", NULL, NULL, "10m", "4s", NULL, NULL, "42s", "-19s Cooldown", "+2s Duration", "+40 Damage Per Second"),
(1, 2, "https://deadlocked.wiki/images/thumb/c/c6/Shoulder_Charge.png/45px-Shoulder_Charge.png", "Shoulder Charge", 
"Charge forward, colliding with enemies and dragging them along. Hitting a wall will Stun enemies caught by Abrams. Speed increased after colliding with enemy Heroes.",
"40

Damage", "0.85s

Stun Duration", NULL, NULL, NULL, NULL, "1.2s", NULL, NULL, "37s", "-19s Cooldown", "+0.5s Duration", "+5.5 Weapon Damage for 8s after colliding with an enemy"),
(1, 3, "https://deadlocked.wiki/images/thumb/5/55/Infernal_Resilience.png/45px-Infernal_Resilience.png", "Infernal Resilience", "Regenerate a portion of incoming damage over time.",
"15%

Damage regenerated", "18s

Regeneration Time", NULL, NULL, 
NULL, NULL, NULL, NULL, NULL, NULL, "+1.5 Health Regen", "+150 Health", "+8% Damage regenerated"),
(1, 4, "https://deadlocked.wiki/images/thumb/a/a1/Seismic_Impact.png/45px-Seismic_Impact.png", "Seismic Impact", 
"Leap high into the air and choose a ground location to crash into. When you hit the ground, all enemies in the radius are damaged and stunned.
Press  to crash down early.",
"150

Damage", "1s

Stun Duration", NULL, NULL,
NULL, NULL, NULL, NULL, NULL, 
"159s",
"+1.5 Health Regen", "+150 Health", "+8% Damage regenerated");