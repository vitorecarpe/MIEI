namespace AntonioCanalizador.Data
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Fatura")]
    public partial class Fatura
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Fatura()
        {
            Fatura_Material = new HashSet<Fatura_Material>();
        }

        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int id_fatura { get; set; }

        public int id_servico { get; set; }

        [Required]
        [StringLength(50)]
        public string nome_servico { get; set; }

        public int id_funcionario { get; set; }

        [Required]
        [StringLength(50)]
        public string nome_funcionario { get; set; }

        public int nif_cliente { get; set; }

        [Required]
        [StringLength(50)]
        public string nome_cliente { get; set; }

        [Required]
        [StringLength(50)]
        public string rua_cliente { get; set; }

        [StringLength(50)]
        public string andar_cliente { get; set; }

        public int porta_cliente { get; set; }

        [Required]
        [StringLength(50)]
        public string localidade_cliente { get; set; }

        [Required]
        [StringLength(50)]
        public string cod_postal_cliente { get; set; }

        [Column(TypeName = "date")]
        public DateTime dia { get; set; }

        public int hora { get; set; }

        public TimeSpan? hora_inicio { get; set; }

        public TimeSpan? hora_fim { get; set; }

        public double? preco_trabalho { get; set; }

        public double? preco_materiais { get; set; }

        public double? preco_total { get; set; }

        public int? ref_pagamento { get; set; }

        [StringLength(255)]
        public string ocorrencias { get; set; }

        public int? estrelas { get; set; }

        [StringLength(255)]
        public string comentarios { get; set; }

        public virtual Cliente Cliente { get; set; }

        public virtual Funcionario Funcionario { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Fatura_Material> Fatura_Material { get; set; }

        public virtual Servico Servico { get; set; }
    }
}
