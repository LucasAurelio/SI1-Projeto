function newTip(){
    document.getElementById('newTip').innerHTML = "<form method='post' action='/newMainTip'>" +
    "<select name='topico' id='form1'>" +
    "<option value='Geral'> Tema </option>" +
    "<option value='Geral'> Geral </option>" +
    "<option value='Laboratórios'> Labs </option>" +
    "<option value='Minitestes'> Minitestes </option>" +
    "<option value='Projeto'> Projeto </option>" +
    "<option value='Heroku'> Heroku </option>" +
    "<option value='PadroesDeProjeto'> Padrões </option>" +
    "<option value='Ferramentas'> Ferramentas </option>" +
    "<option value='Design'> Design </option>" +
    "</select>" +
    "<input name='titulo' id='form2' type='text' placeholder='Título' required/>" +
    "<input name='descricao' id='form3' type='text' placeholder='Digite a sua dica aqui' required/>" + "<br>" +
    "<input class='btn' id='form4' type='submit' value='Adicionar'/>" +
    "</form>";
}

function newLink(){
    document.getElementById('newTip').innerHTML = "<form method='post' action='/newMainLink'>" +
    "<select name='topico' id='form5'>" +
    "<option value='Geral'> Tema </option>" +
    "<option value='Geral'> Geral </option>" +
    "<option value='Laboratórios'> Labs </option>" +
    "<option value='Minitestes'> Minitestes </option>" +
    "<option value='Projeto'> Projeto </option>" +
    "<option value='Heroku'> Heroku </option>" +
    "<option value='PadroesDeProjeto'> Padrões </option>" +
    "<option value='Ferramentas'> Ferramentas </option>" +
    "<option value='Design'> Design </option>" +
    "</select>" +
    "<br>" +
    "<input name='titulo' id='form6 type='text' placeholder='Título' required/>" +
    "<input type='url' name='url' id='form7' placeholder='Digite o seu link aqui' pattern='https?://.+' required>" +
    "<br>" +
    "<input class='btn' id='form8' type='submit' value='Adicionar'/>" +
    "</form>"
}
