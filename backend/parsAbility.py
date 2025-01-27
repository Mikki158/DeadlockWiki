from sys import exception

import pymysql
import pandas as pd
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.webdriver.common.by import By


try:
    connection = pymysql.connect(
        host='sql7.freemysqlhosting.net',
        port=3306,
        user='sql7738074',
        password='Dhdy3HbnQD',
        database='sql7738074',
        cursorclass=pymysql.cursors.DictCursor
    )

    print("successfully connected...")
    print("#" * 20)


except Exception as ex:
    print("Connection refused...")
    print(ex)

cursor = connection.cursor()


with connection.cursor() as cursor:
    sql = """
            DELETE FROM abilities;
            """

    cursor.execute(sql)

connection.commit()

ua = dict(DesiredCapabilities.CHROME)
options = webdriver.ChromeOptions()
options.add_argument('headless')
driver = webdriver.Chrome(options=options)
driver.get("https://deadlocked.wiki/Heroes")

heroes = driver.find_elements(By.XPATH, "/html/body/div[3]/div[3]/div[5]/div[1]/div[1]/div")
for hero in heroes:
    hero.find_element(By.XPATH, "table/tbody/tr[1]/td[2]/b/a").click()
    butons = driver.find_elements(By.XPATH, "/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/ul/li")
    abilities = driver.find_elements(By.XPATH, "/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/div")
#/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/div[2]/div/div[3]/div[1]
    i = 1
    for ability in abilities:
        abilityIcon = ability.find_element(By.XPATH, "div/div[1]/div/div[1]/div[1]/p/span/span/span/img").get_attribute("src")
        #print(abilityIcon)
        abilityName = ability.find_element(By.XPATH, "div/div[1]/div/div[1]/div[1]/p/span[2]").text
        #print(abilityName)

        abilitiesInfo = ability.find_elements(By.XPATH, "div/div[1]/div/div[1]/div[2]/div")

        cooldowns = ability.find_elements(By.XPATH, "div/div[1]/div/div[2]/div")

        distance = None
        range = None
        duration = None



        for abilityInfo in abilitiesInfo:
            try:
                if abilityInfo.find_element(By.XPATH, "p/span[1]/span/a/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/b/be/AttributeIconTechRange.png/20px-AttributeIconTechRange.png":
                    range = abilityInfo.text
                    #print(range)

            except Exception as ex:
                pass
                #print("Нет рэнжа")

            try:
                if abilityInfo.find_element(By.XPATH, "p/span[1]/span/span/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/5/50/AttributeIconTechDuration.png/20px-AttributeIconTechDuration.png":
                    duration = abilityInfo.text
                    #print("время")
                    #print(duration)

            except Exception as ex:
                pass
                #print("Нет времени")

            try:
                if abilityInfo.find_element(By.XPATH, "p/span[1]/span/a/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/6/6a/CastRange.png/20px-CastRange.png":
                    distance = abilityInfo.text
                    #print("дистанция")
                    #print(distance)

            except Exception as ex:
                pass
                #print("Нет дистанции")

        charges = None
        charging_time = None
        сooldown = None

        for cooldown_item in cooldowns:
            try:
                if cooldown_item.find_element(By.XPATH, "div[1]/p/span[1]/span/span/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/6/6f/AttributeIconMaxChargesIncrease.png/20px-AttributeIconMaxChargesIncrease.png":
                    #print("Заряд")
                    charges = cooldown_item.find_element(By.XPATH, "div[1]/p/span[2]/span[1]/b").text
                    #print(charges)

            except Exception as ex:
                pass
                #print("Нет заряда")

            try:
                if cooldown_item.find_element(By.XPATH, "div[2]/p/span[1]/span/a/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/c/ce/AttributeIconTechCooldownBetweenChargeUses.png/15px-AttributeIconTechCooldownBetweenChargeUses.png":
                    #print("Время перезарядки заряда")
                    charging_time = cooldown_item.find_element(By.XPATH, "div[2]/p/span[2]/span[1]/b").text
                    #print(charging_time)

            except Exception as ex:
                pass
                #print("Нет перезарядки заряда")

            try:
                if cooldown_item.find_element(By.XPATH, "p/span[1]/span/a/img").get_attribute("src") == "https://deadlocked.wiki/images/thumb/7/75/AttributeIconTechCooldown.png/20px-AttributeIconTechCooldown.png":
                    #print("Время перезарядки")
                    сooldown = cooldown_item.text
                    #print(сooldown)

            except Exception as ex:
                pass
                #print("Нет времени перезарядки")

        description = ability.find_element(By.XPATH, "div/div[2]/div/div[1]").text
        #print(description)

        characteristic_1 = None
        characteristic_2 = None
        characteristic_3 = None
        characteristic_4 = None
        temp = ""

        characteristics = ability.find_elements(By.XPATH, "div/div[2]/div/div[2]/div")
#/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/div[2]/div/div[2]/div/div[2]/div[1]
#/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/div[3]/div/div[2]/div/div[2]/div[1]
#/html/body/div[3]/div[3]/div[5]/div[1]/div[3]/div[1]/div/div[2]/div/div[2]/div
        j = 0
        for characteristic in characteristics:
            if characteristic.text[0] == 'x':
                x = characteristic.text.find("\n") + 1
                temp = characteristic.text[x:]
                #print("Характеристика обрезанная")
                #print(characteristic_1)
            else:
                #print("Характеристика норм")
                temp = characteristic.text

            if j == 0:
                characteristic_1 = temp
            elif j == 1:
                characteristic_2 = temp
            elif j == 2:
                characteristic_3 = temp

            j += 1
            #print("Характеристика ", j)
            #print(temp)

        upgrade_1 = None
        upgrade_2 = None
        upgrade_3 = None

        upgrades = ability.find_elements(By.XPATH, "div/div[3]/div")

        j = 0
        for upgrade in upgrades:
            temp = upgrade.find_element(By.XPATH, "div[2]")
            if j == 0:
                upgrade_1 = temp.text
            elif j == 1:
                upgrade_2 = temp.text
            elif j == 2:
                upgrade_3 = temp.text
            #print("Улучшение ", j)
            #print(temp.text)
            j += 1



        hero_name = driver.find_element(By.XPATH, "/html/body/div[3]/h1/span").text
        hero_id = ""
        with connection.cursor() as cursor:
            sql = "SELECT id FROM heroes WHERE name = %s"
            values = (hero_name)
            cursor.execute(sql, values)
            data = cursor.fetchone()
            #print("Данные добавлены")
            hero_id = data["id"]
            #print(hero_id)



        with connection.cursor() as cursor:
            sql = """
                    INSERT INTO abilities (hero_id, ability_number, ability_url, ability_name, description, characteristic_1, 
                    characteristic_2, characteristic_3, characteristic_4, distance, ability_range, duration, 
                    charges, charging_time, recharge, upgrade_1, upgrade_2, upgrade_3)
                    VALUES
                    ( %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                    """
            if(charges != None):
                we = int(charges)
            else:
                we = charges
            values = (hero_id, i, abilityIcon, abilityName, description, characteristic_1, characteristic_2,
                       characteristic_3, characteristic_4, distance, range, duration, we, charging_time,
                       сooldown, upgrade_1, upgrade_2, upgrade_3)

            #print(values)
            cursor.execute(sql, values)

        connection.commit()

        if i != 4:
            butons[i].click()
        i += 1

    driver.back()


