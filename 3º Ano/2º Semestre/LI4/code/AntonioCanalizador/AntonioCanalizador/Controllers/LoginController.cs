using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Security;
using AntonioCanalizador.Data;
using AntonioCanalizador.Helpers;


namespace AntonioCanalizador.Controllers
{
    public class LoginController : Controller
    {
        DataContext db = new DataContext();

        public ActionResult Index()
        {
            if (Request.IsAuthenticated && User.Identity.Name != "admin")
            {
                return RedirectToAction("Index", "MyProfile");
            }
            return View();
        }

        public ActionResult loginSucess()
        {
            return RedirectToAction("Home", "Home");
        }

        public ActionResult Registar()
        {
            if (Request.IsAuthenticated)
            {
                return RedirectToAction("Index", "MyProfile");
            }
            return View();
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult Adicionar([Bind(Include = "nif_cliente,nome,data_nascimento,email,password,telemovel,foto,rua,andar,porta,localidade,cod_postal")] Cliente atleta)
        {
            if (ModelState.IsValid)
            {
                atleta.password = MyHelpers.HashPassword(atleta.password);
                db.Clientes.Add(atleta);
                db.SaveChanges();
            }
            return RedirectToAction("Index");
        }

        public ActionResult sucessOperation()
        {
            ViewBag.title = "Atleta adicionado com sucesso";
            ViewBag.mensagem = "Atleta inserido com sucesso";
            return View("_sucessView");
        }

        [System.Web.Mvc.HttpPost]
        public ActionResult Login(string email, string password)
        {
            if (ModelState.IsValid)
            {
                var clientes = (from c in db.Clientes
                               where c.email == email
                               select c);
                if (clientes.ToList<Cliente>().Count > 0)
                {
                    Cliente cliente = clientes.ToList<Cliente>().ElementAt<Cliente>(0);
                    using (MD5 md5Hash = MD5.Create())
                    {
                        if (MyHelpers.VerifyMd5Hash(md5Hash, password, cliente.password))
                        {
                            HttpCookie cookie = MyHelpers.CreateAuthorizeTicket(cliente.nif_cliente.ToString(), "user", 30);
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
            return RedirectToAction("loginSucess", "Login");
        }

        public ActionResult Logout()
        {
            FormsAuthentication.SignOut();
            return RedirectToAction("Index", "Login");
        }

    }
}