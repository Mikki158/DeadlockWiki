import time
import mysql.connector
import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.webdriver.common.by import By

db_config = {
    'user': 'sql7738074',
    'password': 'Dhdy3HbnQD',
    'host': 'sql7.freemysqlhosting.net ',
    'database': 'sql7738074'
}

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

#connection = mysql.connector.connect(db_config)
cursor = connection.cursor()

with connection.cursor() as cursor:
    sql = """
            DELETE FROM heroes_stats;
            """

    cursor.execute(sql)

connection.commit()

with connection.cursor() as cursor:
    sql = """
            ALTER TABLE heroes_stats AUTO_INCREMENT = 1
            """

    cursor.execute(sql)

connection.commit()


ua = dict(DesiredCapabilities.CHROME)
options = webdriver.ChromeOptions()
options.add_argument('headless')
driver = webdriver.Chrome()
driver.get("https://deadlockchaos.ru/deadlock-stats/")

rows = driver.find_elements(By.TAG_NAME, "tr")

for row in rows[1:]:
    cells = row.find_elements(By.TAG_NAME, "td")

    if len(cells) >= 6:
        name = cells[0].text
        winrate = cells[1].text
        pickrate = cells[2].text
        ksmin = float(cells[3].text)
        denmin = float(cells[4].text)
        networth = float(cells[5].text.replace(',', ''))
        kda = float(cells[6].text)

        with connection.cursor() as cursor:
            sql = """
                    INSERT INTO heroes_stats (name, winrate, pickrate, ksmin, kda, denmin, networth) 
                    VALUES (%s, %s, %s, %s, %s, %s, %s)
                    ON DUPLICATE KEY UPDATE 
                        name = VALUES(name),
                        winrate = VALUES(winrate),
                        pickrate = VALUES(pickrate),
                        ksmin = VALUES(ksmin),
                        kda = VALUES(kda),
                        denmin = VALUES(denmin),
                        networth = VALUES(networth)
                    """
            values = (name, winrate, pickrate, ksmin, kda, denmin, networth)
            cursor.execute(sql, values)
            #print("Данные добавлены")



        try:

            connection.commit()
        except mysql.connector.Error as err:
            print(f"Ошибка: {err}")

print("Винрейт запарсен")

driver.quit()
cursor.close()
connection.close()