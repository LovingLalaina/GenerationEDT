// Example matiere.js file
import mongoose from "mongoose";

const matSchema = new mongoose.Schema({
  idMat: {
    type: String,
    required: true,
    unique: true,
  },
  nom: {
    type: String,
    required: true,
  },
});

matSchema.virtual("edt", {
  ref: "Edt",
  localField: "_id",
  foreignField: "Matiere"
})

matSchema.virtual("generate_edt", {
  ref: "generate_Edt",
  localField: "_id",
  foreignField: "Matiere"
})

export default mongoose.model("Mat", matSchema);
