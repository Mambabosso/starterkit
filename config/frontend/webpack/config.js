const path = require("path");

const src = path.resolve(__dirname, "../../..", "src", "main", "resources", "webapp");

const config = {
  entry: path.resolve(src, "starterkit", "index.js"),
  output: "starterkit_bundle.js"
};

const VueLoaderPlugin = require("vue-loader/lib/plugin");


module.exports = {
  mode: "production",
  entry: config.entry,
  output: {
    path: src,
    filename: config.output
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: "vue-loader"
      },
      {
        test: /\.js$/,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-env"]
          }
        }
      },
      {
        test: /\.css$/,
        use: [
          "vue-style-loader",
          "style-loader",
          "css-loader"
        ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin()
  ]
};