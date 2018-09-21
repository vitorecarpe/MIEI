using AntonioCanalizador.Data;
using System.Collections.Generic;

namespace AntonioCanalizador.Models
{
    public class Confirmar
    {
        public Fatura fatura { get; set; }
        public List<Material> material { get; set; }
        public Confirmar()
        {
            this.fatura = new Fatura();
            this.material = new List<Material>();
        }
    }
}