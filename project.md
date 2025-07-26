Criar uma biblioteca de livros
Classes:
- Livro
- Autor
- Usuario
- LivroAlugado

O Usuario pode alugar 1 livro por semana, apenas.
O Livro tem o status booleano de Alugado ou Disponível (pode ser um enum)
O Autor tem uma lista com todos os livros dele na biblioteca

• Usuario
-id
-nome
-email
-livroAlugado = nenhum ou ("id do livro", "nome do livro", "data do aluguel", "data limite de devolução")

• Autor
-id
-nome
- List<Livro>

• Livro
-id
-nome
-dataPublicação
-autor
-enum status (ALUGADO ou DISPONIVEL)

• LivroAlugado
-id
-userId
-livroId
-nomeDoLivro
-dataDoAluguel
-dataLimiteDeDevolucao