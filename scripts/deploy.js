const hre = require("hardhat");

async function main() {
  const FertiliAudit = await hre.ethers.getContractFactory("FertiliAudit");
  console.log("🚀 Fazendo deploy do contrato...");

  const fertiliAudit = await FertiliAudit.deploy();
  await fertiliAudit.deployed(); // <-- Aqui é o correto para ethers v5

  console.log("✅ Contrato deployado com sucesso!");
  console.log("📜 Endereço do contrato:", fertiliAudit.address);
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error("❌ Erro ao fazer deploy:", error);
    process.exit(1);
  });
