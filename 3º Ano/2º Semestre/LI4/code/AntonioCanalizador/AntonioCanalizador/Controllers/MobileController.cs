using AntonioCanalizador.Data;
using AntonioCanalizador.Helpers;
using System;
using System.Linq;
using System.Security.Cryptography;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using AntonioCanalizador.Models;
using System.Net.Mail;

namespace AntonioCanalizador.Controllers
{
    public class MobileController : Controller
    {

        DataContext db = new DataContext();

        public ActionResult Login()
        {
            return View();
        }

        public ActionResult Servico (int fatura)
        {
            var fat = (from f in db.Faturas
                       where f.id_fatura == fatura
                       select f).First();

            var mat = (from m in db.Materials
                       select m).ToList();

            Confirmar conf = new Confirmar();
            conf.fatura = fat;
            conf.material = mat;
            
            return View(conf);
        }

        public ActionResult RegistarServico(string horainicio, string horafim, string material, int quantidade, string ocorrencias, int fatura)
        {
                var fat = (from f in db.Faturas
                           where f.id_fatura == fatura
                           select f).First();

                var cli = (from c in db.Clientes
                           where c.nif_cliente == fat.nif_cliente
                           select c).First();

                var mat = (from m in db.Materials
                           where m.nome == material
                           select m).First();

                var srv = (from s in db.Servicoes
                           where s.id_servico == fat.id_servico
                           select s).First();

                TimeSpan h_inicio = TimeSpan.Parse(horainicio);
                TimeSpan h_fim = TimeSpan.Parse(horafim);

                var total = ((int)Math.Ceiling((h_fim - h_inicio).TotalHours));

                var inicio = (int)Math.Floor(h_inicio.TotalHours);
                var fim = (int)Math.Floor(h_fim.TotalHours);

                var preco_t = 0.0;
                
                if (inicio >= 9 && fim <= 17)
                {
                    preco_t = (srv.preco_normal_p) + (srv.preco_normal_r) * (total - 1);
                }
                else
                {
                    preco_t = (srv.preco_extra_p) + (srv.preco_extra_r) * (total - 1);
                }

                var preco_m = mat.preco * quantidade;

                fat.preco_trabalho = preco_t;
                fat.preco_materiais = preco_m;
                fat.preco_total = preco_t + preco_m;
                fat.hora_inicio = h_inicio;
                fat.hora_fim = h_fim;
                fat.ocorrencias = ocorrencias;
                fat.ref_pagamento = Convert.ToInt32(RandomDigits(9));

                Fatura_Material fatmat = new Fatura_Material();
                fatmat.id_fatura = fatura;
                fatmat.id_material = mat.id_material;
                fatmat.quantidade = quantidade;

                if (ModelState.IsValid)
                {
                    db.Fatura_Material.Add(fatmat);
                    db.SaveChanges();
                }

            enviarEmailSMS(cli.email, cli.telemovel, fat);
                return RedirectToAction("IndexToday");


        }


        static void enviarEmailSMS(string email, int telemovel, Fatura fat)
        {
            // INITIATE SMTP
            SmtpClient smtp = new SmtpClient("smtp.gmail.com", 587);
            smtp.UseDefaultCredentials = false;
            smtp.EnableSsl = true;
            smtp.Credentials = new System.Net.NetworkCredential("dynamite0697@gmail.com", "BersekerBarrage314#");
            //smtp.Credentials = new System.Net.NetworkCredential("geral.antonio.canalizador@gmail.com", "AntonioCanalizador97#");

            var horai = fat.hora_inicio.ToString();
            var horaf = fat.hora_fim.ToString();

            // MESSAGE EMAIL
            MailMessage message = new MailMessage();
            message.To.Add(email);
            message.From = new MailAddress("dynamite0697@gmail.com", "António Canalizador");
            //message.From = new MailAddress("geral.antonio.canalizador@gmail.com", "António Canalizador");
            message.Subject = "Serviço agendado";
            message.Body = string.Concat("O seu serviço de ", fat.nome_servico, " foi realizado com sucesso.","\n",
                "Serviço: ",            fat.nome_servico," \n",    
                "Funcionário: ",        fat.nome_funcionario," \n",
                "Dia: ",                fat.dia.ToShortDateString()," \n",
                "Duração: ",            horai.Remove(horai.Length-3, 3)," - ",horaf.Remove(horai.Length-3, 3)," \n",
                "Preço total: ",        fat.preco_total," €"," \n",
                "Referência MB: 11312/",fat.ref_pagamento," \n",
                "Obrigado por escolher os nossos serviços. Volte sempre.");

            smtp.Send(message);

            // MESSAGE SMS
            MailMessage message2 = new MailMessage(); ;
            message2.To.Add(string.Concat(telemovel, "@txtlocal.co.uk"));
            message2.From = new MailAddress("dynamite0697@gmail.com", "António Canalizador");
            //message2.From = new MailAddress("geral.antonio.canalizador@gmail.com", "António Canalizador");
            message2.Subject = "Serviço agendado";
            message2.Body = string.Concat("O servico foi realizado. A fatura referente está disponível no seu email. Ref. MB: 11312/", fat.ref_pagamento.ToString(), ". Obrigado.");

            smtp.Send(message2);

        }


        public string RandomDigits(int length)
        {
            var random = new Random();
            string s = string.Empty;
            for (int i = 0; i < length; i++)
                s = String.Concat(s, random.Next(10).ToString());
            return s;
        }

        public ActionResult IndexAll()
        {
            if (!Request.IsAuthenticated)
            {
                return RedirectToAction("Login", "Mobile");
            }

            int userid = Convert.ToInt32(User.Identity.Name);

            var fat = (from f in db.Faturas
                       where f.id_funcionario == userid
                       where f.dia >= DateTime.Now
                       where f.preco_total == 0
                       orderby f.dia, f.hora
                       select f).ToList();
            return View(fat);
        }

        public ActionResult IndexTomorrow()
        {
            if (!Request.IsAuthenticated)
            {
                return RedirectToAction("Login", "Mobile");
            }

            int userid = Convert.ToInt32(User.Identity.Name);

            var tomorrow = DateTime.Now.AddDays(1);

            var fat = (from f in db.Faturas
                       where f.id_funcionario == userid
                       where f.dia.Day == tomorrow.Day
                       where f.preco_total == 0
                       orderby f.hora
                       select f).ToList();
            return View(fat);
        }

        public ActionResult IndexToday()
        {
            if (!Request.IsAuthenticated)
            {
                return RedirectToAction("Login", "Mobile");
            }

            int userid = Convert.ToInt32(User.Identity.Name);

            var fat = (from f in db.Faturas
                       where f.id_funcionario == userid
                       where f.dia == DateTime.Today
                       where f.preco_total == 0
                       orderby f.hora
                       select f).ToList();
            return View(fat);
        }

        public ActionResult loginSucess()
        {
            return RedirectToAction("IndexToday", "Mobile");
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult LoginButton(int id, string password)
        {
            if (ModelState.IsValid)
            {
                var funcionarios = (from f in db.Funcionarios
                                where f.id_funcionario == id
                                select f);
                if (funcionarios.ToList<Funcionario>().Count > 0)
                {
                    Funcionario funcionario = funcionarios.ToList<Funcionario>().ElementAt<Funcionario>(0);
                    using (MD5 md5Hash = MD5.Create())
                    {
                        if (MyHelpers.VerifyMd5Hash(md5Hash, password, funcionario.password))
                        {
                            HttpCookie cookie = MyHelpers.CreateAuthorizeTicket(funcionario.id_funcionario.ToString(), "staff", 10);
                            Response.Cookies.Add(cookie);
                        }
                        else
                        {
                            ModelState.AddModelError("password", "Password incorreta!");
                            return View("Index");
                        }
                    }
                }
                else
                {
                    ModelState.AddModelError("", "Dados incorretos. Tente novamente.");
                    return View("Index");

                }
            }
            else
            {
                ModelState.AddModelError("", "Invalid Request");
                return View("Index");

            }
            return RedirectToAction("loginSucess", "Mobile");
        }
        public ActionResult Logout()
        {
            FormsAuthentication.SignOut();
            return RedirectToAction("Login", "Mobile");
        }

        public ActionResult Direcoes()
        {
            return View();
        }

    }
}