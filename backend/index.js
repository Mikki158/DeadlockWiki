//import translate from "translate";

const cron = require('node-cron');
const { exec } = require('child_process');

const express = require('express');
const mysql = require('mysql2');
const dotenv = require('dotenv');
const { error } = require('console');

dotenv.config();

const app = express();
const port = 3000;

//translate.key = process.env.DEEPL_KEY;
//translate.engine = "deepl"; // "google", "yandex", "libre", "deepl"

//results = await translate("Hello World", "ru")print(results)

// Настройка базы данных MySQL
const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  port: process.env.DB_PORT
});

// Подключение к базе данных
db.connect((err) => {
  if (err) {
    console.error('Ошибка подключения к базе данных:', err);
    return;
  }
  console.log('Подключение к базе данных успешно!');
});

// Тестовый маршрут для проверки подключения
app.get('/', (req, res) => {
  res.send('Бэкенд работает!');
});

// Запуск сервера на локальном IP-адресе или на всех интерфейсах
app.listen(port, '0.0.0.0', () => {
    console.log(`Сервер запущен на порту ${port}`);
});

// Маршрут для получения данных (например, героев)
app.get('/heroes_stats', (req, res) => {
  const query = 'SELECT * FROM heroes_stats'; // Замените 'heroes' на название своей таблицы
  console.log('Запрос отправлен!');
  db.query(query, (err, results) => {
    if (err) {
      console.error('Ошибка выполнения запроса:', err);
      res.status(500).send('Ошибка сервера');
      return;
    }
    
    console.log(results);
    res.json(results);
  });
});

app.get('/heroes', (req, res) => {
  const query = 'SELECT * FROM heroes';
  console.log('Запрос отправлен');
  db.query(query, (err, results) => {
    if(err) {
      console.error('Ошибка выполнения запроса:', err);
      res.status(500).send('Ошибка сервера');
      return;
    }
    
    console.log(results);
    res.json(results);
  })
})

app.get('/abilities', (req, res) => {
  const heroID = req.query.heroid;
  console.log("Hero_id = ", heroID);
  const query = 'SELECT ability_url FROM abilities WHERE hero_id = ?';

  db.query(query, [heroID], (err, results) => {
    if(err) {
      console.error('Ошибка выполнения запроса:', err);
      res.status(500).send('Ошибка сервера');
      return;
    } 
    console.log(results);
    res.json(results);
  })
})

app.get('/ability', (req, res) => {
  const heroID = req.query.heroid;
  const abilityNumber = req.query.ability_number;
  console.log("ability = ", heroID);
  const query = 'SELECT * FROM abilities WHERE hero_id = ? AND ability_number = ?';

  db.query(query, [heroID, abilityNumber], (err, results) => {
    if(err) {
      console.error('Ошибка выполнения запроса:', err);
      res.status(500).send('Ошибка сервера');
      return;
    } 
    console.log(results);
    res.json(results);
  })
})

app.get('/hero_id', (req, res) => {
  const heroName = req.query.hero_name;
  const query = 'SELECT id FROM heroes WHERE name = ?'

  db.query(query, [heroName], (err, results))
})

app.get('/news', (req, res) => {
  const query = 'SELECT * FROM news';
  console.log('Запрос отправлен');
  db.query(query, (err, results) => {
    if(err) {
      console.error('Ошибка выполнения запроса:', err);
      res.status(500).send('Ошибка сервера');
      return;
    }

    console.log(results);
    res.json(results);
  })
})

// Запуск сервера
app.listen(port, () => {
  console.log(`Сервер запущен на http://localhost:${port}`);
});

function runPythonScript(path) { 
  exec(`python ${path}`, (error, stdout, stderr) => {
    if (error) {
      console.error(`Ошибка при выполнении скрипта: ${error.message}`);
      return;
    }
    if (stderr) {
      console.error(`stderr: ${stderr}`);
      return;
    }
    console.log(`stdout: ${stdout}`);
  })
}

cron.schedule('0 */12 * * *', () => {
  runPythonScript('D:/deadlock/backend/parsWinrate.py');
  runPythonScript('D:/deadlock/backend/parsNews.py');
  runPythonScript('D:/deadlock/backend/parsHero.py');
})

