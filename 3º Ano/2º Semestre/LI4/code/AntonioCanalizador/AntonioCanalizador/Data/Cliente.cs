namespace AntonioCanalizador.Data
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Cliente")]
    public partial class Cliente
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Cliente()
        {
            Faturas = new HashSet<Fatura>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int nif_cliente { get; set; }

        [Required]
        [StringLength(50)]
        public string nome { get; set; }

        [Column(TypeName = "date")]
        public DateTime data_nascimento { get; set; }

        [Required]
        [StringLength(50)]
        public string email { get; set; }

        [Required]
        [StringLength(50)]
        public string password { get; set; }

        public int telemovel { get; set; }

        [StringLength(255)]
        public string foto { get; set; }

        [Required]
        [StringLength(255)]
        public string rua { get; set; }

        [StringLength(50)]
        public string andar { get; set; }

        public int porta { get; set; }

        [Required]
        [StringLength(50)]
        public string localidade { get; set; }

        [Required]
        [StringLength(50)]
        public string cod_postal { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Fatura> Faturas { get; set; }
    }
}
