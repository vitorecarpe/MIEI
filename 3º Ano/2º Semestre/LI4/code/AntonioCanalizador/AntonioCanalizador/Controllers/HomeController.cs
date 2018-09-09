using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using AntonioCanalizador.Data;
using AntonioCanalizador.Helpers;

namespace AntonioCanalizador.Controllers
{

    public class HomeController : Controller
    {
        DataContext db = new DataContext();

        public ActionResult Index()
        {
            if (Utils.fBrowserIsMobile())
            {
                return RedirectToAction("IndexToday", "Mobile");
            }

            if (Request.IsAuthenticated && User.Identity.Name!="admin")
            {
                return RedirectToAction("Home", "Home");
            }
            var des = (from c in db.Servicoes where c.id_tipo == 1 select c.nome).ToList();
            var por = (from c in db.Servicoes where c.id_tipo == 2 select c.nome).ToList();
            var can = (from c in db.Servicoes where c.id_tipo == 3 select c.nome).ToList();
            var fur = (from c in db.Servicoes where c.id_tipo == 4 select c.nome).ToList();

            ViewData["desentupimentos"] = des;
            ViewData["portas"] = por;
            ViewData["canalizacao"] = can;
            ViewData["furosdagua"] = fur;
            return View();
        }

        public ActionResult Home()
        {
            if (Utils.fBrowserIsMobile())
            {
                return RedirectToAction("IndexToday", "Mobile");
            }

            if (!Request.IsAuthenticated || User.Identity.Name=="admin")
            {
                return RedirectToAction("Index", "Home");
            }
            var des = (from c in db.Servicoes where c.id_tipo == 1 select c.nome).ToList();
            var por = (from c in db.Servicoes where c.id_tipo == 2 select c.nome).ToList();
            var can = (from c in db.Servicoes where c.id_tipo == 3 select c.nome).ToList();
            var fur = (from c in db.Servicoes where c.id_tipo == 4 select c.nome).ToList();

            ViewData["desentupimentos"] = des;
            ViewData["portas"] = por;
            ViewData["canalizacao"] = can;
            ViewData["furosdagua"] = fur;
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Maintenance()
        {
            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}