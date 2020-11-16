//
//  DetailsViewController.swift
//  Marvel
//
//  Created by Diego on 16/11/2020.
//

import UIKit

class DetailsViewController: UIViewController {

    @IBOutlet weak var loadingIndicatior: UIActivityIndicatorView!
    @IBOutlet weak var image: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var desc: UILabel!
    var id: Int!

    override func viewDidLoad() {
        super.viewDidLoad()
        Cliente.getCharacterById(id: id) { (response, error) in
            self.loadingIndicatior.isHidden = true
            if let error = error {
                print(error.localizedDescription)
                return
            }
            if let response = response {
                self.image.sd_setImage(with: response.thumbnailUrl(imageVariant: "landscape_incredible"), placeholderImage: UIImage(systemName: "photo"))
                self.name.text = response.name
                self.desc.text = response.description

            }
        }
    }
    

}
