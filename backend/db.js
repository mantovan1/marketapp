const { Sequelize } = require('sequelize');
require('dotenv').config();

// Conexão com o banco de dados MySQL
const sequelize = new Sequelize('market', 'root', '', {

	host: 'localhost',
	port: 3306,
	dialect: 'mysql'
});

//console.log(process.env.MYSQL_DATABASE + 'teste');

module.exports = sequelize;
