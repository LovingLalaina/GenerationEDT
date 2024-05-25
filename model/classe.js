import mongoose from "mongoose";

const classeSchema = new mongoose.Schema({
  idClasse: {
    type: String,
    required: true,
    unique: true,
  },
  capacite: {
    type: String,
    required: true,
  },
});

classeSchema.virtual("generate_edt", {
  ref: "generate_Edt",
  localField: "_id",
  foreignField: "classe"
})

export default mongoose.model("Classe", classeSchema);
