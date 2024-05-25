import mongoose from "mongoose";

const niveauSchema = new mongoose.Schema({
  idNiveau: {
    type: String,
    required: true,
    unique: true,
  },
  nom: {
    type: String,
    required: true,
  },
  groupe: {
    type: String,
    required:true
  },
  nombreEleve: {
    type: Number,
    required: true
  }
});

niveauSchema.virtual("generate_edt", {
  ref: "generate_Edt",
  localField: "_id",
  foreignField: "niveau"
})

export default mongoose.model("Niveau", niveauSchema);
