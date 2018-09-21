using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Mvc;
using AntonioCanalizador.Data;

namespace AntonioCanalizador.Controllers
{
    public class MyProfileController : Controller
    {

        DataContext db = new DataContext();

        [System.Web.Mvc.Authorize]
        public ActionResult Index()
        {
            if (!Request.IsAuthenticated || User.Identity.Name == "admin")
            {
                return RedirectToAction("Index", "Home");
            }

            int userid = Convert.ToInt32(User.Identity.Name);

            var cliente = (from c in db.Clientes
                           where c.nif_cliente == userid
                           select c).First();
            return View(cliente);
        }

        public ActionResult VerAgendados()
        {
            if (!Request.IsAuthenticated)
            {
                return RedirectToAction("Index", "Login");
            }
            else
            {
                int userid = Convert.ToInt32(User.Identity.Name);

                var faturas = (from f in db.Faturas
                               where f.nif_cliente == userid
                               where f.hora_inicio == null
                               orderby f.dia
                               select f).ToList();
                
                return View(faturas);
            }
        }
        public ActionResult Historico()
        {
            if (!Request.IsAuthenticated)
            {
                return RedirectToAction("Index", "Login");
            }
            else
            {
                int userid = Convert.ToInt32(User.Identity.Name);

                var faturas = (from f in db.Faturas
                               where f.nif_cliente == userid
                               where f.hora_inicio != null
                               orderby f.dia descending
                               select f).ToList();
                return View(faturas);
            }
        }

        public ActionResult Detalhes(int id)
        {
            var fatura = (from f in db.Faturas
                           where f.id_fatura == id
                           select f).First();
            return View(fatura);
        }

        public ActionResult Comentar(string comentario, int estrelas, int id_fatura)
        {
            var fat = (from f in db.Faturas
                       where f.id_fatura == id_fatura
                       select f).First();

            fat.comentarios = comentario;
            fat.estrelas = estrelas;
            db.SaveChanges();

            return RedirectToAction("Detalhes", new { id = id_fatura});
        }

        // GET api/<controller>
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<controller>/5
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        public void Post([FromBody]string value)
        {
        }

        // PUT api/<controller>/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/5
        public void Delete(int id)
        {
        }
    }
}