namespace AntonioCanalizador.Data
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Funcionario")]
    public partial class Funcionario
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Funcionario()
        {
            Faturas = new HashSet<Fatura>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int id_funcionario { get; set; }

        [Required]
        [StringLength(50)]
        public string nome { get; set; }

        public int ativo { get; set; }

        public int turno { get; set; }

        [Required]
        [StringLength(50)]
        public string password { get; set; }

        public int telemovel { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Fatura> Faturas { get; set; }
    }
}
