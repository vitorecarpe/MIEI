namespace AntonioCanalizador.Data
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Servico")]
    public partial class Servico
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Servico()
        {
            Faturas = new HashSet<Fatura>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int id_servico { get; set; }

        [Required]
        [StringLength(50)]
        public string nome { get; set; }

        [Required]
        [StringLength(255)]
        public string descricao { get; set; }

        public int disponibilidade { get; set; }

        [StringLength(255)]
        public string foto { get; set; }

        public double preco_normal_p { get; set; }

        public double preco_normal_r { get; set; }

        public double preco_extra_p { get; set; }

        public double preco_extra_r { get; set; }

        public int id_tipo { get; set; }

        public int duracaoprevista { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Fatura> Faturas { get; set; }

        public virtual Tipo Tipo { get; set; }
    }
}
