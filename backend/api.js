const express = require('express');

const Sequelize = require('sequelize');
const Op = Sequelize.Op;

const app = express();

const Produto = require('./models/Produto');

app.use(express.static('uploads'));

app.get('/', async (req, res) => {
	res.send('api market');
})

app.get('/produtos', async (req, res) => {
	
	const produtos = await Produto.findAll();
	res.json(produtos);
})

app.get('/produtos/nome/:nome', async (req, res) => {

	const produtos = await Produto.findAll( {where: {
		nome: {
			[Op.like]: '%' + req.params.nome + '%'
		}
	}} );

	res.json(produtos);

})

app.get('/produtos/qrcode/:qrcode', async (req, res) => {

        const produto = await Produto.findOne( {where: {cod: req.params.qrcode}});
        res.json(produto);
})


app.listen(8080, function() {
	console.log('api rodando na porta 8080');
})
