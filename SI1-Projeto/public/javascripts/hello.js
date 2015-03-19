function newTip(){
    document.getElementById('newTip').innerHTML = "<form method='post' action='/newMainTip'>" +
    "<input name='titulo' style='width:30%' type='text' placeholder='Título' required/>" + "<br>" + "<br>" +
    "<input name='descricao' style='width:30%' type='text' placeholder='Digite aqui sua dica!' required/>" + "<br>" + "<br>" +
    "<select name='topico' style='width:30%'>" +
    "<option value='Geral'> Tópico </option>" +
    "<option value='Geral'> Geral </option>" +
    "<option value='Laboratórios'> Labs </option>" +
    "<option value='Minitestes'> Minitestes </option>" +
    "<option value='Projeto'> Projeto </option>" +
    "<option value='Heroku'> Heroku </option>" +
    "<option value='PadroesDeProjeto'> Padrões </option>" +
    "<option value='Ferramentas'> Ferramentas </option>" +
    "<option value='Design'> Design </option>" +
    "</select>" + "<br>" + "<br>" +
    "<input class='btn' type='submit' value='Adicionar'/>"
    + "</form>";
}