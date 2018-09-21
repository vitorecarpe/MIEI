using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using AntonioCanalizador.Data;
using AntonioCanalizador.Models;
using AntonioCanalizador.Helpers;
using System.Net.Mail;

namespace AntonioCanalizador.Controllers
{
    public class ServicosController : Controller
    {
        DataContext db = new DataContext();


        [System.Web.Mvc.Authorize]
        public ActionResult Agendar(string servico, int erro)
        {
            Servico serv = (from s in db.Servicoes where s.nome == servico select s).First();
            Agendar ag = new Agendar();
            ag.servico = serv;
            ag.error = erro;
            return View(ag);
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult Confirmar(DateTime date, string time, string nome)
        {
            int error_id = 42;
            var h = new string(time.TakeWhile(char.IsDigit).ToArray());
            int hora = Convert.ToInt32(h);
            int userid = Convert.ToInt32(User.Identity.Name);

            if (date < DateTime.Today) error_id = 1;
            else if (date == DateTime.Today && hora <= DateTime.Now.Hour) error_id = 2;
            else
            {
                var funcs = (from func in db.Funcionarios
                             select func).ToList();

                int turno = 0;
                if (hora >= 9 && hora < 17) turno = 1;
                else if (hora >= 17 && hora < 23) turno = 2;
                else if (hora > 0 && hora < 1) turno = 2;
                else if (hora >= 1 && hora < 9) turno = 3;

                var servico = (from s in db.Servicoes
                               where s.nome == nome
                               select s).First();

                for (var i = 0; i < funcs.Count(); i++)
                {
                    var idfunc = funcs[i].id_funcionario;
                    var agenda = (from f in db.Faturas
                                  join s in db.Servicoes
                                  on f.id_servico equals s.id_servico
                                  where ((f.hora <= hora && f.hora + s.duracaoprevista > hora) || (hora+servico.duracaoprevista>f.hora))
                                  where f.dia == date
                                  where f.preco_total==null
                                  where f.id_funcionario == idfunc
                                  select f).Any();

                    if (agenda || funcs[i].turno != turno) { funcs.RemoveAt(i); i--; }
                }

                if (funcs.Count()==0) error_id = 3;
                else
                {
                    

                    var cliente = (from c in db.Clientes
                                   where c.nif_cliente == userid
                                   select c).First();

                    Fatura fatura = new Fatura();
                    fatura.id_servico = servico.id_servico;
                    fatura.nome_servico = servico.nome;
                    fatura.id_funcionario = funcs.First().id_funcionario;
                    fatura.nome_funcionario = funcs.First().nome;
                    fatura.nif_cliente = cliente.nif_cliente;
                    fatura.nome_cliente = cliente.nome;
                    fatura.rua_cliente = cliente.rua;
                    fatura.andar_cliente = cliente.andar;
                    fatura.porta_cliente = cliente.porta;
                    fatura.localidade_cliente = cliente.localidade;
                    fatura.cod_postal_cliente = cliente.cod_postal;
                    fatura.dia = date;
                    fatura.hora = hora;
                    fatura.hora_inicio = null;
                    fatura.hora_fim = null;
                    fatura.preco_materiais = 0;
                    fatura.preco_total = 0;
                    fatura.preco_trabalho = 0;
                    fatura.ref_pagamento = 0;
                    fatura.ocorrencias = "";
                    fatura.estrelas = 0;
                    fatura.comentarios = "";

                    enviarEmailSMS(cliente.email, cliente.telemovel, fatura.dia.ToString("d MMM"), fatura.hora, fatura.nome_servico);
                    db.Faturas.Add(fatura);
                    db.SaveChanges();


                }
            }
            return RedirectToAction("Agendar", new { servico = nome , erro = error_id});
        }

        static void enviarEmailSMS(string email, int telemovel, string dia, int hora, string servico)
        {
            
            // INITIATE SMTP
            SmtpClient smtp = new SmtpClient("smtp.gmail.com", 587);
            smtp.UseDefaultCredentials = false;
            smtp.EnableSsl = true;
            smtp.Credentials = new System.Net.NetworkCredential("dynamite0697@gmail.com", "BersekerBarrage314#");
            //smtp.Credentials = new System.Net.NetworkCredential("geral.antonio.canalizador@gmail.com", "AntonioCanalizador97#");


            // MESSAGE EMAIL
            MailMessage message = new MailMessage();
            message.To.Add(email);
            message.From = new MailAddress("dynamite0697@gmail.com", "António Canalizador");
            //message.From = new MailAddress("geral.antonio.canalizador@gmail.com", "António Canalizador");
            message.Subject = "Serviço agendado";
            message.Body = string.Concat("O seu serviço de ", servico, " foi agendado com sucesso para o dia ", dia, " às ", hora,"h. Obrigado.");

            smtp.Send(message);

            // MESSAGE SMS
            MailMessage message2 = new MailMessage(); ;
            message2.To.Add(string.Concat(telemovel,"@txtlocal.co.uk"));
            message2.From = new MailAddress("dynamite0697@gmail.com", "António Canalizador");
            //message2.From = new MailAddress("geral.antonio.canalizador@gmail.com", "António Canalizador");
            message2.Subject = "Serviço agendado";
            message2.Body = string.Concat("O seu servico de ", servico, " foi agendado com sucesso para o dia ", dia, " as ", hora, "h. Obrigado.");

            smtp.Send(message2);
       
        }

    }
}
