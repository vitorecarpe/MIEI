namespace AntonioCanalizador.Data
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class DataContext : DbContext
    {
        public DataContext()
            : base("name=DataContext")
        {
        }

        public virtual DbSet<Cliente> Clientes { get; set; }
        public virtual DbSet<Fatura> Faturas { get; set; }
        public virtual DbSet<Fatura_Material> Fatura_Material { get; set; }
        public virtual DbSet<Funcionario> Funcionarios { get; set; }
        public virtual DbSet<Material> Materials { get; set; }
        public virtual DbSet<Servico> Servicoes { get; set; }
        public virtual DbSet<Tipo> Tipoes { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cliente>()
                .Property(e => e.nome)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.email)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.password)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.foto)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.rua)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.andar)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.localidade)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .Property(e => e.cod_postal)
                .IsUnicode(false);

            modelBuilder.Entity<Cliente>()
                .HasMany(e => e.Faturas)
                .WithRequired(e => e.Cliente)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.nome_servico)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.nome_funcionario)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.nome_cliente)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.rua_cliente)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.andar_cliente)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.localidade_cliente)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.cod_postal_cliente)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.hora_inicio)
                .HasPrecision(0);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.hora_fim)
                .HasPrecision(0);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.ocorrencias)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .Property(e => e.comentarios)
                .IsUnicode(false);

            modelBuilder.Entity<Fatura>()
                .HasMany(e => e.Fatura_Material)
                .WithRequired(e => e.Fatura)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Funcionario>()
                .Property(e => e.nome)
                .IsUnicode(false);

            modelBuilder.Entity<Funcionario>()
                .Property(e => e.password)
                .IsUnicode(false);

            modelBuilder.Entity<Funcionario>()
                .HasMany(e => e.Faturas)
                .WithRequired(e => e.Funcionario)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Material>()
                .Property(e => e.nome)
                .IsUnicode(false);

            modelBuilder.Entity<Material>()
                .HasMany(e => e.Fatura_Material)
                .WithRequired(e => e.Material)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Servico>()
                .Property(e => e.nome)
                .IsUnicode(false);

            modelBuilder.Entity<Servico>()
                .Property(e => e.descricao)
                .IsUnicode(false);

            modelBuilder.Entity<Servico>()
                .Property(e => e.foto)
                .IsUnicode(false);

            modelBuilder.Entity<Servico>()
                .HasMany(e => e.Faturas)
                .WithRequired(e => e.Servico)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Tipo>()
                .Property(e => e.nome)
                .IsUnicode(false);

            modelBuilder.Entity<Tipo>()
                .HasMany(e => e.Servicoes)
                .WithRequired(e => e.Tipo)
                .WillCascadeOnDelete(false);
        }
    }
}
