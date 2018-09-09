using AntonioCanalizador.Data;
using AntonioCanalizador.Helpers;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Security.Cryptography;
using System.Web;
using System.Web.Mvc;

namespace AntonioCanalizador.Controllers
{
    public class AdminController : Controller
    {
        DataContext db = new DataContext();

        public ActionResult Login()
        {
            return View();
        }

        public ActionResult Home()
        {
            return View();
        }

        public ActionResult RemoveFunc(int id)
        {
            var func = (from f in db.Funcionarios
                       where f.id_funcionario == id
                       select f).First();

            func.ativo = 0;
            db.SaveChanges();

            return RedirectToAction("VerFuncs");
        }
        public ActionResult RemoveServ(int id)
        {
            var serv = (from s in db.Servicoes
                        where s.id_servico == id
                        select s).First();

            db.Servicoes.Remove(serv);
            db.SaveChanges();

            return RedirectToAction("VerServs");
        }

        public ActionResult EditarFunc(int id)
        {
            var func = (from f in db.Funcionarios
                               where f.id_funcionario == id
                               select f).First();

            return View(func);
        }
        public ActionResult EditarServ(int id)
        {
            var serv = (from s in db.Servicoes
                        where s.id_servico == id
                        select s).First();

            return View(serv);
        }

        public ActionResult VerFuncs()
        {
            var funcs = (from f in db.Funcionarios
                         where f.ativo==1
                         select f).ToList();
            return View(funcs);
        }
        public ActionResult VerServs()
        {
            var servs = (from s in db.Servicoes
                         select s).ToList();
            return View(servs);
        }

        public ActionResult RegistarFunc()
        {
            return View();
        }
        public ActionResult RegistarServ()
        {
            return View();
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult RegFunc([Bind(Include = "id_funcionario,nome,ativo,turno,password,telemovel")] Funcionario funcionario)
        {
            if (ModelState.IsValid)
            {
                funcionario.password = MyHelpers.HashPassword(funcionario.password);
                db.Funcionarios.Add(funcionario);
                db.SaveChanges();
            }
            return RedirectToAction("Home");
        }
        [System.Web.Mvc.HttpPost]
        public ActionResult RegServ([Bind(Include = "id_servico,nome,descricao,disponibilidade,foto,preco_normal_p,preco_normal_r,preco_extra_p,preco_extra_r,id_tipo,duracaoprevista")] Servico servico)
        {
            if (ModelState.IsValid)
            {
                db.Servicoes.Add(servico);
                db.SaveChanges();
            }
            return RedirectToAction("Home");
        }

        public ActionResult SaveFunc(int telemovel, int turno, int id_funcionario)
        {
            var func = (from f in db.Funcionarios
                       where f.id_funcionario == id_funcionario
                       select f).First();

            func.telemovel = telemovel;
            func.turno = turno;
            db.SaveChanges();

            return RedirectToAction("EditarFunc", new { id = id_funcionario });
        }
        public ActionResult SaveServ(string nome, string descricao, int disponibilidade, string foto, int preco_normal_p, int preco_normal_r, int preco_extra_p, int preco_extra_r, int duracaoprevista, int id_servico)
        {
            var serv = (from s in db.Servicoes
                        where s.id_servico == id_servico
                        select s).First();

            serv.nome = nome;
            serv.descricao = descricao;
            serv.disponibilidade = disponibilidade;
            serv.foto = foto;
            serv.preco_normal_p = preco_normal_p;
            serv.preco_normal_r = preco_normal_r;
            serv.preco_extra_p = preco_extra_p;
            serv.preco_extra_r = preco_extra_r;
            serv.duracaoprevista = duracaoprevista;
            db.SaveChanges();

            return RedirectToAction("EditarServ", new { id = id_servico });
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult LoginButton(string id, string senha)
        {
            if (ModelState.IsValid)
            {
                if (id=="admin")
                {
                    using (MD5 md5Hash = MD5.Create())
                    {
                        if (MyHelpers.VerifyMd5Hash(md5Hash, senha, "21232f297a57a5a743894a0e4a801fc3"))
                        {
                            HttpCookie cookie = MyHelpers.CreateAuthorizeTicket(id, "admin", 30);
                            Response.Cookies.Add(cookie);
                        }
                        else
                        {
                            ModelState.AddModelError("password", "Password incorreta!");
                            return View("Login");
                        }
                    }
                }
                else
                {
                    ModelState.AddModelError("", "Dados incorretos. Tente novamente.");
                    return View("Login");

                }
            }
            else
            {
                ModelState.AddModelError("", "Invalid Request");
                return View("Login");

            }
            return RedirectToAction("loginSucess", "Admin");
        }
        public ActionResult loginSucess()
        {
            return RedirectToAction("Home", "Admin");
        }

    }
}