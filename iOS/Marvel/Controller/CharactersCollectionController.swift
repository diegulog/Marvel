//
//  CharactersCollectionController.swift
//  Marvel
//
//  Created by Diego on 16/11/2020.
//

import UIKit
import SDWebImage

class CharactersCollectionController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
    
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var loadingIndicatior: UIActivityIndicatorView!
    @IBOutlet weak var flowLayout: UICollectionViewFlowLayout!
    @IBOutlet weak var errorLabel: UILabel!
    @IBOutlet weak var retryButton: UIButton!
    let limit = 20
    var offset = 0
    var characters = [Character]()
    var totalItems = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        configFlowLayout()
        loadMoreItems(limit: limit, offset: offset)
        loadingUI(loading: true)
    }
    
    
    func loadMoreItems(limit: Int, offset: Int) {

        Cliente.getCharacteres(limit: limit, offset: offset) { (response, error) in
            self.loadingUI(loading: false)
            if let error = error {
                self.setError(mensaje: error.localizedDescription)
                return
            }
            if let dataResponse = response {
                self.totalItems = dataResponse.total
                dataResponse.results.forEach { character in
                    self.characters.append(character)
                }
                self.collectionView.reloadData()
                let endIndex = min(self.totalItems, self.offset + self.limit)
                print("Loading items form \(self.offset) to \(endIndex)")
                self.offset = endIndex
            }
          
        }
        
    }
    func loadingUI(loading: Bool){
        if loading {
            loadingIndicatior.startAnimating()
        }else{
            loadingIndicatior.stopAnimating()
        }
        errorLabel.isHidden = true
        retryButton.isHidden = true
    }
    
    func setError(mensaje: String){
        self.errorLabel.text = mensaje
        self.errorLabel.isHidden = false
        self.retryButton.isHidden = false
        print(mensaje)
    }
    
    func configFlowLayout() {
        let space:CGFloat = 2.0
        let dimension = (view.frame.size.width - (2 * space)) / 2.0
        flowLayout.minimumInteritemSpacing = space
        flowLayout.minimumLineSpacing = space
        flowLayout.itemSize = CGSize(width: dimension, height: dimension)
    }
    
    @IBAction func retry(_ sender: Any) {
        characters = [Character]()
        loadingUI(loading: true)
        loadMoreItems(limit: self.limit, offset: self.offset)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let id = characters[indexPath.row].id
        performSegue(withIdentifier: "details", sender: id)
    }
    

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return characters.count
    }
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let character = characters[indexPath.row]
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CollectionViewCell", for: indexPath) as! CharacterCollectionViewCell
        cell.image.sd_setImage(with: character.thumbnailUrl(imageVariant: "standard_xlarge"), placeholderImage: UIImage(systemName: "photo"))
        cell.name.text = character.name
        if indexPath.row == characters.count - 1 {
            if totalItems > characters.count {
                loadMoreItems(limit: self.limit, offset: self.offset)
            }
        }
        return cell
    }
    
    
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return  1
    }
    
    
    // -------------------------------------------------------------------------
    // MARK: - Navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "details" {
            let vc = segue.destination as! DetailsViewController
            let id = sender as! Int
            vc.id = id
        }
    }

}

