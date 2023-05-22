const db = require('../db');
const { Sequelize } = require('sequelize');

const Produto = db.define('produtos', {
	id: {
		type: Sequelize.INTEGER,
		allowNull: false,
		autoIncrement: true,
		primaryKey: true
	},
	nome: {
		type: Sequelize.STRING,
		allowNull: false,
		unique: true
	},
	preco: {
		type: Sequelize.DOUBLE,
		allowNull: false,
		unique: false
	},
	cod: {
                type: Sequelize.STRING,
		allowNull: true,
		unique: true
        },
	foto: {
		type: Sequelize.STRING,
		allowNull: false,
		unique: true
	}
})

//Produto.sync({force: true});

module.exports = Produto;
