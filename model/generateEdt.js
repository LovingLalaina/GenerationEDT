import mongoose from "mongoose";

const generateSchema = new mongoose.Schema({
  matiere: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Mat", // Reference to the Mat model
    required: true,
  },
  heure: {
    type: String, // You can use a specific type for hours if needed
    required: true,
  },
  date: {
    type: Date,
    required: true,
  },
  prof: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Prof",
    required: true,
  },
  niveau: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Niveau", // Reference to the Niveau model
    required: true,
  },
  classe: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Classe", // Reference to the Classe model
    required: true,
  },
});

export default mongoose.model("generateEdt", generateSchema);
