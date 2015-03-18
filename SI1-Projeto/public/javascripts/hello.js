function newTip(){
    document.getElementById('newTip').innerHTML = "<form method='post' action='/newMainTip'>" +
    "<input name='titulo' type='text' placeholder='Título' required/>" +
    "<input name='descricao' type='text' placeholder='Digite aqui sua dica!' required/>" +
    "<select name='topico'>" +
    "<option value='Geral'> Tópico </option>" +
    "<option value='Geral'> Geral </option>" +
    "<option value='Laboratórios'> Labs </option>" +
    "<option value='Minitestes'> Minitestes </option>" +
    "<option value='Projeto'> Projeto </option>" +
    "<option value='Heroku'> Heroku </option>" +
    "<option value='PadroesDeProjeto'> Padrões </option>" +
    "<option value='Ferramentas'> Ferramentas </option>" +
    "<option value='Design'> Design </option>" +
    "<input class='btn' type='submit' value='Adicionar'/>";
}