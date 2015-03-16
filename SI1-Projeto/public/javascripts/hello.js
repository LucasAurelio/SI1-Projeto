function Labs() {
  document.getElementById('showing').innerHTML = "Welcome to your Play application's JavaScript!";
}

function Minitestes() {
  document.getElementById('showing').innerHTML = "Welcome to your Play application's JavaScript!2";
}

function Projeto() {
  document.getElementById('showing').innerHTML = "Welcome to your Play application's JavaScript!3";
}

function Links() {
  document.getElementById('showing').innerHTML = conteudoLinks();
}

function Faq() {
  document.getElementById('showing').innerHTML = conteudoFAQ();
}

function conteudoFAQ() {
  return "<b>1. O que é o portal do leite?</b>" +
      "<p>O portal do leite é um sistema que ajuda os estudantes do curso de ciência da computação a melhor cursar a disciplina de " +
      "Sistemas de Informaçao I.</p>" +
      "<b>2. Como o sistema funciona?</b>" +
      "<p>Como aluno, é possível clicar em um tema e adiciono uma dica no tema. " +
      "A dica pode ser de diferentes tipos: o que o aluno precisa saber para não ter dificuldades, " +
      "disciplinas anteriores que podem auxiliar ao aluno, um link de algum material útil, ou apenas conselho que ache importante.</p>" +
      "<b>3. Como melhorar minha experiência?</b>" +
      "<p>Cada usuário pode, além de postar um dica, avaliar as demais disponíveis sobre um determinado tema, da uma nota. Assim como denunciar alguma" +
      "dica que contenha conteúdo inapropriado.</p>";
}

function conteudoLinks(){
    return "<h4>Site da Disciplina</h4>" +
        "<a href='https://sites.google.com/a/computacao.ufcg.edu.br/si1/home' target='_blank'>Sistema de Informação I</a>" +
        "<h4>Bootstrap</h4>" +
        "<a href='https://docs.google.com/document/d/13kIXJoagChDouQ1AuLYUKM150O2uyJ0OsB1khU3Zdzc/edit?usp=sharing'>Utilizando o bootstrap</a>" +
        "<h4>JavaScript/JQuery</h4>" +
        "<a href='https://docs.google.com/document/d/1v0EgiHCvABxET3oM0qna-fdIiVQntYgCeH0ycl9lpCg/edit?usp=sharing'>Dicas e materiais Javascript/JQuery</a>" +
        "<h4>Git/github</h4>" +
        "<a href='http://try.github.io/levels/1/' target='blank' rel='nofollow'>Tutorial expresso e interativo na web</a>" +
        "<h4>Chamadas à APIs</h4>" +
        "<a href='https://docs.google.com/document/d/12HB5eH4SAfVGTvxSvhlPG0JGCSevMtUyRgvoP6N9R20/edit'>Links úteis e algo mais sobre chamadas à APIs</a>";
}

function conteudoProjeto(){
    /*implementar a parte que pega as dicas sobre os projetos*/
}

function conteudoMinitestes(){
    /*implementar a parte que pega as dicas sobre os minitestes*/
}

function conteudoLabs(){
    /*implementar a parte que pega as dicas sobre os labs*/
}