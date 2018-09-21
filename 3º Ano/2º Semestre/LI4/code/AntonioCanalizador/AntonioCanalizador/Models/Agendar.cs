using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using AntonioCanalizador.Data;

namespace AntonioCanalizador.Models
{
    public class Agendar
    {
        public Servico servico { get; set; }
        public int error { get; set; }
        public Agendar()
        {
            this.servico = new Servico();
            this.error = 0;
        }
    }
}