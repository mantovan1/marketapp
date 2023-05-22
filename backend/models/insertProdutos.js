const db = require('../db');

const Produto = require('./Produto');

Produto.bulkCreate([
			{nome: "Mouse e Teclado Multilaser", preco: 39.99, cod: "ML-TC212-COU21-February 2020-01", foto: "ML-TC212-COU21-February 2020-01.jpeg"},
			{nome: "Celular Xiaomi Redmi Note 9", preco: 599.99, cod: "GR62103X255915C000P00000", foto: "GR62103X255915C000P00000.jpeg"},
			{nome: "Earphones Stereo PMCELL", preco: 19.99, cod: "http://qr12.cn/BjoUIa", foto: "http://qr12.cn/BjoUIa.jpeg"},
			{nome: "Pilhas Recarregáveis Multilaser", preco: 39.99, cod: "ML-CB045-RAY50-April  2020-04", foto: "ML-CB045-RAY50-April  2020-04.jpeg"}
]).then(() => console.log("Users data have been saved"));	
