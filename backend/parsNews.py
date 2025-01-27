from itertools import count
from zlib import decompress
from translate import Translator

import pymysql
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from transliterate import translit, get_available_language_codes

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
print("1")

cursor = connection.cursor()
translator = Translator(from_lang="ru", to_lang="en")

with connection.cursor() as cursor:
    sql = """
            DELETE FROM news;
            """

    cursor.execute(sql)

connection.commit()

with connection.cursor() as cursor:
    sql = """
            ALTER TABLE news AUTO_INCREMENT = 1
            """

    cursor.execute(sql)

connection.commit()

print("2")
ua = dict(DesiredCapabilities.CHROME)
options = webdriver.ChromeOptions()
options.add_argument('headless')
driver = webdriver.Chrome()
print("3")
driver.get("https://deadlock.one/news")
print("4")
i = 2
j = 1

button = driver.find_element(By.XPATH, "/html/body/div[2]/main/div/div[2]/div/div/div[3]/div/div/nav/ul/li[8]")

count = len(driver.find_elements(By.XPATH, "/html/body/div[2]/main/div/div[2]/div/div/div[3]/div/div/nav/ul/li")) - 2
#/html/body/div[2]/main/div/div[2]/div/div/div[3]/div/div/div[3]/div
#/html/body/div[2]/main/div/div[2]/div/div/div[3]/div/div/nav/ul/li[1]/a
#button.click()

for i in range(count):
    news = driver.find_elements(By.XPATH, "/html/body/div[2]/main/div/div[2]/div/div/div[3]/div/div/div[3]/div")
    for oneNews in news:
        imageUrl = ""
        title = ""
        description = ""
        date = ""
        link = ""

        imageUrl = oneNews.find_element(By.XPATH, "div/div/div[1]/div/a/picture/div/img").get_attribute("src")
        title = oneNews.find_element(By.XPATH, "div/div/h5/span/a").text
        description = oneNews.find_element(By.XPATH, "div/div/div[2]/div").text
        date = oneNews.find_element(By.XPATH, "div/div/div[4]/span/time").text
        link = oneNews.find_element(By.XPATH, "div/div/h5/span/a").get_attribute("href")

        title = translator.translate(title)
        description = translator.translate(description)
        date = translator.translate(date)

        #title = translit(title, language_code='ru', reversed=True)
        #description = translit(description, language_code='ru', reversed=True)
        #date = translit(date, language_code='ru', reversed=True)

        #print(imageUrl)
        #print(title)
        #print(description)
        #print(date)
        #print(link)
        #print("-----------------------")

        # if j >= count:
        # buttons[i].click()

        

        with connection.cursor() as cursor:
            sql = """
                    INSERT INTO news (imageUrl, title, description, date, link)
                    VALUES
                    ( %s, %s, %s, %s, %s)
                    """

            values = (imageUrl, title, description, date, link)

            cursor.execute(sql, values)

        connection.commit()

    driver.get("https://deadlock.one/news" + "?page=" + str(j))
    j += 1

print("Новости запарсены")
